package com.rit.notification.context.mail

import com.fasterxml.jackson.databind.ObjectMapper
import com.rit.notification.SingleArgumentSpy
import com.rit.notification.domain.notification.mail.MailSenderService
import com.rit.notification.domain.notification.mail.MailTemplate
import com.rit.robusta.util.Strings
import com.rit.starterboot.domain.notification.MailNotification
import com.rit.starterboot.domain.notification.RegistrationOtpMailNotification
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(excludeAutoConfiguration = [SecurityAutoConfiguration])
@ContextConfiguration(classes = [MailNotificationEndpoint, MailNotificationService])
class MailNotificationEndpointSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Shared
    ObjectMapper objectMapper

    @SpringBean
    MailSenderService mailSenderService = Mock()

    def setupSpec() {
        objectMapper = new ObjectMapper()
    }

    def "send mail, with registration otp template, expect status is ok"() {
        setup:
        def argumentSpy = new SingleArgumentSpy<MailTemplate>(MailTemplate)
        mailSenderService.send(_) >> { args -> argumentSpy.spy(args) }
        when:
        def result = sendMail(new RegistrationOtpMailNotification(recipient, otp))
        then:
        result.andExpect(status().isOk())
        def template = argumentSpy.value
        template != null
        template.subject.contains(otp)
        template.recipient == recipient
        Strings.isNotBlank(template.templateName)

        where:
        recipient = "test@example.com"
        otp = "123456"
    }

    ResultActions sendMail(MailNotification template) {
        def request = post("/mail")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(template))
        return mockMvc.perform(request)
    }
}
