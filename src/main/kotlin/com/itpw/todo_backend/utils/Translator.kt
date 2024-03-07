package com.itpw.todo_backend.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.stereotype.Component

@Component
class Translator @Autowired internal constructor(
    private val messageSource: ResourceBundleMessageSource,
) {

    fun toLocale(msgCode: String, vararg strings: String): String {
        val locale = LocaleContextHolder.getLocale()
        val message = messageSource.getMessage(msgCode, null, locale)
        var replaceArgNum = 0
        return message.replace("""[{]{2}[}]{2}""".toRegex()) {
            strings[replaceArgNum++]
        }
    }
}