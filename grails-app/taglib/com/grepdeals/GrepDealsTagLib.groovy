package com.grepdeals

class GrepDealsTagLib {
    static namespace = 'gd';

    def textFieldRow = { attrs ->
        String labelCodeDefault = attrs.remove('labelCodeDefault')
        String name = getRequiredAttribute(attrs, 'name', 'textFieldRow')
        String labelCode = getRequiredAttribute(attrs, 'labelCode', 'textFieldRow')
        def value = getRequiredAttribute(attrs, 'value', 'textFieldRow')
        def labelSpan = getRequiredAttribute(attrs, 'labelSpan', 'textFieldRow')
        def fieldSpan = getRequiredAttribute(attrs, 'fieldSpan', 'textFieldRow')

        def fieldAttributes = [name: name, value: value] + attrs

        out << """
		<div class="column span-${labelSpan}">
			<label for="${name}">${message(code: labelCode, default: labelCodeDefault)}</label>
	    </div>
		<div class="column span-${fieldSpan} last">
			${textField(fieldAttributes)}
		</div>
		<hr class="space"/>
		"""
    }

    def customFieldRow = { attrs, body ->
        String labelCodeDefault = attrs.remove('labelCodeDefault')
        String name = getRequiredAttribute(attrs, 'name', 'customFieldRow')
        String labelCode = getRequiredAttribute(attrs, 'labelCode', 'customFieldRow')
        def labelSpan = getRequiredAttribute(attrs, 'labelSpan', 'customFieldRow')
        def fieldSpan = getRequiredAttribute(attrs, 'fieldSpan', 'customFieldRow')

        out << """
		<div class="column span-${labelSpan}">
			<label for="${name}">${message(code: labelCode, default: labelCodeDefault)}</label>
	    </div>
		<div class="column span-${fieldSpan} last">
			${body()}
		</div>
		<hr class="space"/>
		"""
    }




    protected getRequiredAttribute(attrs, String name, String tagName) {
        if (!attrs.containsKey(name)) {
            throwTagError("Tag [$tagName] is missing required attribute [$name]")
        }
        attrs.remove name
    }
}
