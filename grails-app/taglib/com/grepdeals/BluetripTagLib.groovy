package com.grepdeals

class BluetripTagLib {
    static namespace = "bluetrip"

    static RESOURCE_MAPPINGS = [
        css:[type:"text/css", rel:'stylesheet'],
        rss:[type:'application/rss+xml', rel:'alternate'],
        atom:[type:'application/atom+xml', rel:'alternate'],
        favicon:[type:'image/x-icon', rel:'shortcut icon'],
        appleicon:[type:'image/x-icon', rel:'apple-touch-icon']
    ]

    def resources = { attrs ->
        out << resourceLink(type:'css', dir:'bluetrip/css', file:'screen.css', media:'screen, projection')
        out << resourceLink(type:'css', dir:'bluetrip/css', file:'print.css', media:'print')
        out << """
<!--[if lt IE 8]>${resourceLink(type:'css', dir:'bluetrip/css', file:'ie.css', media:'screen, projection')}<![endif]-->
"""
        out << resourceLink(type:'css', dir:'bluetrip/css', file:'style.css', media:'screen, projection')

    }

    def resourceLink = { attrs ->
        def t = attrs.remove('type')
        def typeInfo = RESOURCE_MAPPINGS[t]
        if (!typeInfo) {
            throwTagError "Unknown resourceLink type: ${t}"
        }
        def o = new StringBuilder()
        def url = g.resource(dir:attrs.remove('dir'), file:attrs.remove('file'))
        o << "<link href=\"${url.encodeAsHTML()}\" "
        // Output info from the mappings
        typeInfo.each { k, v ->
            o << k
            o << '="'
            o << v
            o << '" '
        }

        // Output any remaining user-specified attributes
        attrs.each { k, v ->
            o << k
            o << '="'
            o << v.encodeAsHTML()
            o << '" '
        }

        o << '/>'
        out << o
    }
}