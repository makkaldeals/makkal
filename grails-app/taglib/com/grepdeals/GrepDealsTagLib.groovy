package com.grepdeals

class GrepDealsTagLib {
    static namespace = 'gd';

    private class GdTagAttributes {

        private def labelCodeDefault;
        private def labelCode;
        private def name;
        private def value;
        private def labelSpan;
        private def fieldSpan;

        public GdTagAttributes(attrs, tagName) {

            //override value with optionValue for selects
            if (attrs.optionValue){
                attrs.value = attrs.remove("optionValue") ;
            }

            labelCodeDefault = attrs.remove('labelCodeDefault');

            name = getRequiredAttribute(attrs, 'name', tagName);
            labelCode = getAttribute(attrs, 'labelCode', tagName, isLabelCodeRequired(tagName));
            value = getRequiredAttribute(attrs, 'value', tagName);
            labelSpan = getRequiredAttribute(attrs, 'labelSpan', tagName);
            fieldSpan = getRequiredAttribute(attrs, 'fieldSpan', tagName);
        }

        private boolean isLabelCodeRequired(String tagName) {
            switch (tagName) {
                case "submitButtonRow":
                    return false;
                default: return true;
            }
        }
    }

    def textFieldRow = { attrs ->

        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'textFieldRow');

        def fieldAttributes = [name: gdTagAttrs.name, value: gdTagAttrs.value] + attrs

        def tagBody = {
            textField(fieldAttributes);
        }
        renderTag(gdTagAttrs, tagBody);
    }


     def countrySelectRow = { attrs ->

        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'countrySelectRow');

        def fieldAttributes = [name: gdTagAttrs.name, optionValue: gdTagAttrs.value] + attrs
        def tagBody = {
            countrySelect(fieldAttributes);
        }
        renderTag(gdTagAttrs, tagBody);
    }

     def selectFieldRow = { attrs ->

        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'selectFieldRow');

        def fieldAttributes = [name: gdTagAttrs.name, optionValue: gdTagAttrs.value] + attrs
        def tagBody = {
            select(fieldAttributes);
        }
        renderTag(gdTagAttrs, tagBody);
    }

    def customFieldRow = { attrs, body ->
        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'customFieldRow');

        def fieldAttributes = [name: gdTagAttrs.name, value: gdTagAttrs.value] + attrs

        def tagBody = {
            body();
        }
        renderTag(gdTagAttrs, tagBody);

    }

    def ckeditorRow = { attrs ->
        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'ckeditorRow');

        def fieldAttributes = [name: gdTagAttrs.name,
                toolbar: "custom",
                filebrowserImageBrowseUrl: "",
                filebrowserBrowseUrl: "",
                filebrowserFlashBrowseUrl: "",
                filebrowserImageUploadUrl: createLink(controller: 'media', action: 'uploadImage'),
                filebrowserUploadUrl: createLink(controller: 'media', action: 'uploadImage')] + attrs;

        def tagBody = {
            gdTagAttrs.value ? ckeditor.editor(fieldAttributes, gdTagAttrs.value) : ckeditor.editor(fieldAttributes);
        }
        renderTag(gdTagAttrs, tagBody);

    }

    def dateFieldRow = { attrs ->

        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'dateFieldRow');

        def fieldAttributes = [name: gdTagAttrs.name, value: gdTagAttrs.value] + attrs

        def tagBody = {
            jqDatePicker(fieldAttributes);
        }
        renderTag(gdTagAttrs, tagBody);
    }


    def submitButtonRow = { attrs ->

        GdTagAttributes gdTagAttrs = new GdTagAttributes(attrs, 'submitButtonRow');

        def fieldAttributes = [name: gdTagAttrs.name, value: gdTagAttrs.value] + attrs

        out << """
		<div class="column span-${gdTagAttrs.fieldSpan} prepend-${gdTagAttrs.labelSpan} last">
			${submitButton(fieldAttributes)}
		</div>
		<hr class="space"/>
		"""
    }

    private renderTag(GdTagAttributes gdTagAttrs, Closure tagBody) {
        out << """
		<div class="column span-${gdTagAttrs.labelSpan}">
			<label for="${gdTagAttrs.name}">${message(code: gdTagAttrs.labelCode, default: gdTagAttrs.labelCodeDefault)}</label>
	    </div>
		<div class="column span-${gdTagAttrs.fieldSpan} last">
			${tagBody()}
		</div>
		<hr class="space"/>
		"""
    }

    protected getAttribute(attrs, String name, String tagName, boolean isRequired) {
        if (isRequired && !attrs.containsKey(name)) {
            throwTagError("Tag [$tagName] is missing required attribute [$name]")
        }
        attrs.remove name
    }

    protected getRequiredAttribute(attrs, String name, String tagName) {
        return getAttribute(attrs, name, tagName, true);
    }

}
