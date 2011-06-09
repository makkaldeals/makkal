package com.grepdeals

class GrepDealsTagLib {
    static namespace = 'gd';

    private static class GdTagAttributes {
        private def labelCode;
        private def name;
        private def value;
        private def labelSpan;
        private def fieldSpan;

        public GdTagAttributes(attrs, tagName) {
            name = getRequiredAttribute(attrs, 'name', tagName);
            labelCode = getRequiredAttribute(attrs, 'labelCode', tagName);
            value = getRequiredAttribute(attrs, 'value', tagName);
            labelSpan = getRequiredAttribute(attrs, 'labelSpan', tagName);
            fieldSpan = getRequiredAttribute(attrs, 'fieldSpan', tagName);
        }
    }

    def textFieldRow = { attrs ->
        String labelCodeDefault = attrs.remove('labelCodeDefault')
        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'textFieldRow');

        def fieldAttributes = [name: gdTagAttrs.name, value: gdTagAttrs.value] + attrs

        out << """
		<div class="column span-${gdTagAttrs.labelSpan}">
			<label for="${gdTagAttrs.name}">${message(code: gdTagAttrs.labelCode, default: labelCodeDefault)}</label>
	    </div>
		<div class="column span-${gdTagAttrs.fieldSpan} last">
			${textField(fieldAttributes)}
		</div>
		<hr class="space"/>
		"""
    }

    def customFieldRow = { attrs, body ->
        String labelCodeDefault = attrs.remove('labelCodeDefault')
        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'customFieldRow');

        def fieldAttributes = [name: gdTagAttrs.name, value: gdTagAttrs.value] + attrs

        out << """
		<div class="column span-${gdTagAttrs.labelSpan}">
			<label for="${gdTagAttrs.name}">${message(code: gdTagAttrs.labelCode, default: labelCodeDefault)}</label>
	    </div>
		<div class="column span-${gdTagAttrs.fieldSpan} last">
			${body()}
		</div>
		<hr class="space"/>
		"""
    }

    def ckeditorRow = { attrs ->
        String labelCodeDefault = attrs.remove('labelCodeDefault')
        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'ckeditorRow');

        def fieldAttributes = [name: gdTagAttrs.name,
                toolbar: "custom",
                filebrowserImageBrowseUrl: "",
                filebrowserBrowseUrl: "",
                filebrowserFlashBrowseUrl: "",
                filebrowserImageUploadUrl: createLink(controller: 'media', action: 'uploadImage'),
                filebrowserUploadUrl: createLink(controller: 'media', action: 'uploadImage')] + attrs;

        out << """
		<div class="column span-${gdTagAttrs.labelSpan}">
			<label for="${gdTagAttrs.name}">${message(code: gdTagAttrs.labelCode, default: labelCodeDefault)}</label>
	    </div>
		<div class="column span-${gdTagAttrs.fieldSpan} last">
		    ${gdTagAttrs.value ? ckeditor.editor(fieldAttributes, gdTagAttrs.value) : ckeditor.editor(fieldAttributes)}
		</div>
		<hr class="space"/>
		"""
    }


    private static  getRequiredAttribute(attrs, String name, String tagName) {
        if (!attrs.containsKey(name)) {
            throwTagError("Tag [$tagName] is missing required attribute [$name]")
        }
        attrs.remove name
    }


}
