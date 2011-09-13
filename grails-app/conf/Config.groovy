import java.awt.Color
import java.awt.Font

import org.apache.log4j.DailyRollingFileAppender

import com.grepdeals.auth.User
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator
import com.octo.captcha.component.image.color.SingleColorGenerator
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator
import com.octo.captcha.component.image.textpaster.NonLinearTextPaster
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator
import com.octo.captcha.engine.GenericCaptchaEngine
import com.octo.captcha.image.gimpy.GimpyFactory
import com.octo.captcha.service.multitype.GenericManageableCaptchaService

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text/plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        all: '*/*',
        json: ['application/json', 'text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

grails.plugin.cloudfoundry.username = 'admin@grepdeals.com'
grails.plugin.cloudfoundry.password = 'grepdeals2011'

grepdeals {
    user {
        admin.email = "admin@grepdeals.com"
        admin.password = "grepdeals2011"
    }
    posts {
        max.per.page = 5;
        expiresOn.maxDuration = "+1m"; //a number of days from today ('y' for years, 'm' for months, 'w' for weeks, 'd' for days, e.g. '+1m +1w')
        expiresOn.defaultDurationInDays = 7;
    }
    image {
        max.upload.size = 1600;
        picasa.feedUrl = "https://picasaweb.google.com/data/feed/api/user/${grepdeals.user.admin.email}";
    }
}

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%d{yy/MM/dd HH:mm:ss.SSS} %-5p [%t] [%c{1}] %m%n')
        appender new DailyRollingFileAppender(name: 'makkalDealsServerLog', fileName: 'logs/makkalDealsServer.log', layout: pattern(conversionPattern: '%d{yy/MM/dd HH:mm:ss.SSS} %-5p [%t] [%c{1}] %m%n'))
    }


    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    warn 'org.mortbay.log',
            'grails.app.service.grails.buildtestdata.BuildTestDataService',
            'grails.buildtestdata.DomainInstanceBuilder'

    root {
        info 'stdout', 'grepDealsServerLog'
        additivity = true
    }

}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.grepdeals.auth.User'
grails.plugins.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.grepdeals.auth.UserRole'
grails.plugins.springsecurity.authority.className = 'com.grepdeals.auth.Role'
grails.plugins.springsecurity.rememberMe.persistent = true
grails.plugins.springsecurity.rememberMe.persistentToken.domainClassName = 'com.grepdeals.auth.PersistentLogin'

//TODO REMOVE GRAILS SIMPLE BLOG ONCE DONE WITH POSTING
grails.blog.author.evaluator = {
    User.get(session.user.id).business.name;
};

ckeditor {
    config = "/js/ckconfig.gsp"
    skipAllowedItemsCheck = false
    defaultFileBrowser = "ofm"
    upload {
        basedir = "/uploads/"
        overwrite = true
        link {
            browser = true
            upload = true
            allowed = []
            denied = ['html', 'htm', 'php', 'php2', 'php3', 'php4', 'php5',
                    'phtml', 'pwml', 'inc', 'asp', 'aspx', 'ascx', 'jsp',
                    'cfm', 'cfc', 'pl', 'bat', 'exe', 'com', 'dll', 'vbs', 'js', 'reg',
                    'cgi', 'htaccess', 'asis', 'sh', 'shtml', 'shtm', 'phtm']
        }
        image {
            browser = true
            upload = true
            allowed = ['jpg', 'gif', 'jpeg', 'png', 'txt']
            denied = []
        }
        flash {
            browser = false
            upload = false
            allowed = ['swf']
            denied = []
        }
    }
}


jcaptchas {
    image = new GenericManageableCaptchaService(
            new GenericCaptchaEngine(
                    new GimpyFactory(
                            new RandomWordGenerator(
                                    "abcdefghijklmnopqrstuvwxyz1234567890"
                            ),
                            new ComposedWordToImage(
                                    new RandomFontGenerator(
                                            20, // min font size
                                            30, // max font size
                                            [new Font("Arial", 0, 10)] as Font[]
                                    ),
                                    new GradientBackgroundGenerator(
                                            140, // width
                                            35, // height
                                            new SingleColorGenerator(new Color(0, 60, 0)),
                                            new SingleColorGenerator(new Color(20, 20, 20))
                                    ),
                                    new NonLinearTextPaster(
                                            6, // minimal length of text
                                            6, // maximal length of text
                                            new Color(0, 255, 0)
                                    )
                            )
                    )
            ),
            180, // minGuarantedStorageDelayInSeconds
            180000 // maxCaptchaStoreSize
    )

    // Uncomment this to enable the sound captcha, you must install FreeTTS for it to work though.
    /*	sound = new DefaultManageableSoundCaptchaService()*/
}

// Added by the JQuery Validation plugin:
jqueryValidation.packed = true
jqueryValidation.cdn = false  // false or "microsoft"
jqueryValidation.additionalMethods = false

// Added by the JQuery Validation UI plugin:
jqueryValidationUi {
    errorClass = 'error'
    validClass = 'valid'
    onsubmit = true
    renderErrorsOnTop = false

    qTip {
        packed = true
        classes = 'ui-tooltip-red ui-tooltip-shadow ui-tooltip-rounded'
    }

    /*
       Grails constraints to JQuery Validation rules mapping for client side validation.
       Constraint not found in the ConstraintsMap will trigger remote AJAX validation.
     */
    StringConstraintsMap = [
            blank: 'required', // inverse: blank=false, required=true
            creditCard: 'creditcard',
            email: 'email',
            inList: 'inList',
            minSize: 'minlength',
            maxSize: 'maxlength',
            size: 'rangelength',
            matches: 'matches',
            notEqual: 'notEqual',
            url: 'url',
            nullable: 'required',
            unique: 'unique',
            validator: 'validator'
    ]

    // Long, Integer, Short, Float, Double, BigInteger, BigDecimal
    NumberConstraintsMap = [
            min: 'min',
            max: 'max',
            range: 'range',
            notEqual: 'notEqual',
            nullable: 'required',
            inList: 'inList',
            unique: 'unique',
            validator: 'validator'
    ]

    CollectionConstraintsMap = [
            minSize: 'minlength',
            maxSize: 'maxlength',
            size: 'rangelength',
            nullable: 'required',
            validator: 'validator'
    ]

    DateConstraintsMap = [
            min: 'minDate',
            max: 'maxDate',
            range: 'rangeDate',
            notEqual: 'notEqual',
            nullable: 'required',
            inList: 'inList',
            unique: 'unique',
            validator: 'validator'
    ]

    ObjectConstraintsMap = [
            nullable: 'required',
            validator: 'validator'
    ]

    CustomConstraintsMap = [
            phone: 'true', // International phone number validation
            phoneUS: 'true',
            alphanumeric: 'true',
            letterswithbasicpunc: 'true',
            lettersonly: 'true'
    ]
}
facebook.applicationSecret='5e82473f8d5ce5b068a56ab380e089a2'
facebook.applicationId='123013621134012'
