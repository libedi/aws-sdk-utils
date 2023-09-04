/**
 * 
 */
package io.github.libedi.awssdkutils.ses.event.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.libedi.awssdkutils.ses.event.type.NotificationType;

/** SesMessageTest
 *
 * @author "Sangjun,Park"
 *
 */
class SesMessageTest {

    ObjectMapper mapper;

    @BeforeEach
    void init() {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @DisplayName("Bounce 테스트")
    @Test
    void bounce() throws Exception {
        // given
        String json = createBounceSample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.BOUNCE);
        assertThat(actual.getMail()).isNotNull();
        assertThat(actual.getBounce()).isNotNull();
        System.out.println(actual);
    }

    private String createBounceSample() {
        return "{\r\n"
                + "  \"eventType\":\"Bounce\",\r\n"
                + "  \"bounce\":{\r\n"
                + "    \"bounceType\":\"Permanent\",\r\n"
                + "    \"bounceSubType\":\"General\",\r\n"
                + "    \"bouncedRecipients\":[\r\n"
                + "      {\r\n"
                + "        \"emailAddress\":\"recipient@example.com\",\r\n"
                + "        \"action\":\"failed\",\r\n"
                + "        \"status\":\"5.1.1\",\r\n"
                + "        \"diagnosticCode\":\"smtp; 550 5.1.1 user unknown\"\r\n"
                + "      }\r\n"
                + "    ],\r\n"
                + "    \"timestamp\":\"2017-08-05T00:41:02.669Z\",\r\n"
                + "    \"feedbackId\":\"01000157c44f053b-61b59c11-9236-11e6-8f96-7be8aexample-000000\",\r\n"
                + "    \"reportingMTA\":\"dsn; mta.example.com\"\r\n"
                + "  },\r\n"
                + "  \"mail\":{\r\n"
                + "    \"timestamp\":\"2017-08-05T00:40:02.012Z\",\r\n"
                + "    \"source\":\"Sender Name <sender@example.com>\",\r\n"
                + "    \"sourceArn\":\"arn:aws:ses:us-east-1:123456789012:identity/sender@example.com\",\r\n"
                + "    \"sendingAccountId\":\"123456789012\",\r\n"
                + "    \"messageId\":\"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "    \"destination\":[\r\n"
                + "      \"recipient@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"headersTruncated\":false,\r\n"
                + "    \"headers\":[\r\n"
                + "      {\r\n"
                + "        \"name\":\"From\",\r\n"
                + "        \"value\":\"Sender Name <sender@example.com>\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"To\",\r\n"
                + "        \"value\":\"recipient@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"Subject\",\r\n"
                + "        \"value\":\"Message sent from Amazon SES\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"MIME-Version\",\r\n"
                + "        \"value\":\"1.0\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"Content-Type\",\r\n"
                + "        \"value\":\"multipart/alternative; boundary=\\\"----=_Part_7307378_1629847660.1516840721503\\\"\"\r\n"
                + "      }\r\n"
                + "    ],\r\n"
                + "    \"commonHeaders\":{\r\n"
                + "      \"from\":[\r\n"
                + "        \"Sender Name <sender@example.com>\"\r\n"
                + "      ],\r\n"
                + "      \"to\":[\r\n"
                + "        \"recipient@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"messageId\":\"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "      \"subject\":\"Message sent from Amazon SES\"\r\n"
                + "    },\r\n"
                + "    \"tags\":{\r\n"
                + "      \"ses:configuration-set\":[\r\n"
                + "        \"ConfigSet\"\r\n"
                + "      ],\r\n"
                + "      \"ses:source-ip\":[\r\n"
                + "        \"192.0.2.0\"\r\n"
                + "      ],\r\n"
                + "      \"ses:from-domain\":[\r\n"
                + "        \"example.com\"\r\n"
                + "      ],\r\n"
                + "      \"ses:caller-identity\":[\r\n"
                + "        \"ses_user\"\r\n"
                + "      ]\r\n"
                + "    }\r\n"
                + "  }\r\n"
                + "}";
    }

    @DisplayName("Complaint 테스트")
    @Test
    void complaint() throws Exception {
        // given
        String json = createComplaintSample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.COMPLAINT);
        assertThat(actual.getMail()).isNotNull();
        assertThat(actual.getComplaint()).isNotNull();
        System.out.println(actual);
    }

    private String createComplaintSample() {
        return "{\r\n"
                + "  \"eventType\":\"Complaint\",\r\n"
                + "  \"complaint\": {\r\n"
                + "    \"complainedRecipients\":[\r\n"
                + "      {\r\n"
                + "        \"emailAddress\":\"recipient@example.com\"\r\n"
                + "      }\r\n"
                + "    ],\r\n"
                + "    \"timestamp\":\"2017-08-05T00:41:02.669Z\",\r\n"
                + "    \"feedbackId\":\"01000157c44f053b-61b59c11-9236-11e6-8f96-7be8aexample-000000\",\r\n"
                + "    \"userAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36\",\r\n"
                + "    \"complaintFeedbackType\":\"abuse\",\r\n"
                + "    \"arrivalDate\":\"2017-08-05T00:41:02.669Z\"\r\n"
                + "  },\r\n"
                + "  \"mail\":{\r\n"
                + "    \"timestamp\":\"2017-08-05T00:40:01.123Z\",\r\n"
                + "    \"source\":\"Sender Name <sender@example.com>\",\r\n"
                + "    \"sourceArn\":\"arn:aws:ses:us-east-1:123456789012:identity/sender@example.com\",\r\n"
                + "    \"sendingAccountId\":\"123456789012\",\r\n"
                + "    \"messageId\":\"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "    \"destination\":[\r\n"
                + "      \"recipient@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"headersTruncated\":false,\r\n"
                + "    \"headers\":[\r\n"
                + "      {\r\n"
                + "        \"name\":\"From\",\r\n"
                + "        \"value\":\"Sender Name <sender@example.com>\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"To\",\r\n"
                + "        \"value\":\"recipient@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"Subject\",\r\n"
                + "        \"value\":\"Message sent from Amazon SES\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"MIME-Version\",\"value\":\"1.0\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"Content-Type\",\r\n"
                + "        \"value\":\"multipart/alternative; boundary=\\\"----=_Part_7298998_679725522.1516840859643\\\"\"\r\n"
                + "      }\r\n"
                + "    ],\r\n"
                + "    \"commonHeaders\":{\r\n"
                + "      \"from\":[\r\n"
                + "        \"Sender Name <sender@example.com>\"\r\n"
                + "      ],\r\n"
                + "      \"to\":[\r\n"
                + "        \"recipient@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"messageId\":\"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "      \"subject\":\"Message sent from Amazon SES\"\r\n"
                + "    },\r\n"
                + "    \"tags\":{\r\n"
                + "      \"ses:configuration-set\":[\r\n"
                + "        \"ConfigSet\"\r\n"
                + "      ],\r\n"
                + "      \"ses:source-ip\":[\r\n"
                + "        \"192.0.2.0\"\r\n"
                + "      ],\r\n"
                + "      \"ses:from-domain\":[\r\n"
                + "        \"example.com\"\r\n"
                + "      ],\r\n"
                + "      \"ses:caller-identity\":[\r\n"
                + "        \"ses_user\"\r\n"
                + "      ]\r\n"
                + "    }\r\n"
                + "  }\r\n"
                + "}";
    }

    @DisplayName("Delivery 테스트")
    @Test
    void delivery() throws Exception {
        // given
        String json = createDeliverySample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.DELIVERY);
        assertThat(actual.getMail()).isNotNull();
        assertThat(actual.getDelivery()).isNotNull();
        System.out.println(actual);
    }

    private String createDeliverySample() {
        return "{\r\n"
                + "  \"eventType\": \"Delivery\",\r\n"
                + "  \"mail\": {\r\n"
                + "    \"timestamp\": \"2016-10-19T23:20:52.240Z\",\r\n"
                + "    \"source\": \"sender@example.com\",\r\n"
                + "    \"sourceArn\": \"arn:aws:ses:us-east-1:123456789012:identity/sender@example.com\",\r\n"
                + "    \"sendingAccountId\": \"123456789012\",\r\n"
                + "    \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "    \"destination\": [\r\n"
                + "      \"recipient@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"headersTruncated\": false,\r\n"
                + "    \"headers\": [\r\n"
                + "      {\r\n"
                + "        \"name\": \"From\",\r\n"
                + "        \"value\": \"sender@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"To\",\r\n"
                + "        \"value\": \"recipient@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Subject\",\r\n"
                + "        \"value\": \"Message sent from Amazon SES\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"MIME-Version\",\r\n"
                + "        \"value\": \"1.0\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Content-Type\",\r\n"
                + "        \"value\": \"text/html; charset=UTF-8\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Content-Transfer-Encoding\",\r\n"
                + "        \"value\": \"7bit\"\r\n"
                + "      }\r\n"
                + "    ],\r\n"
                + "    \"commonHeaders\": {\r\n"
                + "      \"from\": [\r\n"
                + "        \"sender@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"to\": [\r\n"
                + "        \"recipient@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "      \"subject\": \"Message sent from Amazon SES\"\r\n"
                + "    },\r\n"
                + "    \"tags\": {\r\n"
                + "      \"ses:configuration-set\": [\r\n"
                + "        \"ConfigSet\"\r\n"
                + "      ],\r\n"
                + "      \"ses:source-ip\": [\r\n"
                + "        \"192.0.2.0\"\r\n"
                + "      ],\r\n"
                + "      \"ses:from-domain\": [\r\n"
                + "        \"example.com\"\r\n"
                + "      ],\r\n"
                + "      \"ses:caller-identity\": [\r\n"
                + "        \"ses_user\"\r\n"
                + "      ],\r\n"
                + "      \"ses:outgoing-ip\": [\r\n"
                + "        \"192.0.2.0\"\r\n"
                + "      ],\r\n"
                + "      \"myCustomTag1\": [\r\n"
                + "        \"myCustomTagValue1\"\r\n"
                + "      ],\r\n"
                + "      \"myCustomTag2\": [\r\n"
                + "        \"myCustomTagValue2\"\r\n"
                + "      ]      \r\n"
                + "    }\r\n"
                + "  },\r\n"
                + "  \"delivery\": {\r\n"
                + "    \"timestamp\": \"2016-10-19T23:21:04.133Z\",\r\n"
                + "    \"processingTimeMillis\": 11893,\r\n"
                + "    \"recipients\": [\r\n"
                + "      \"recipient@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"smtpResponse\": \"250 2.6.0 Message received\",\r\n"
                + "    \"reportingMTA\": \"mta.example.com\"\r\n"
                + "  }\r\n"
                + "}";
    }

    @DisplayName("Send 테스트")
    @Test
    void send() throws Exception {
        // given
        String json = createSendSample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.SEND);
        assertThat(actual.getMail()).isNotNull();
        System.out.println(actual);
    }

    private String createSendSample() {
        return "{\r\n"
                + "  \"eventType\": \"Send\",\r\n"
                + "  \"mail\": {\r\n"
                + "    \"timestamp\": \"2016-10-14T05:02:16.645Z\",\r\n"
                + "    \"source\": \"sender@example.com\",\r\n"
                + "    \"sourceArn\": \"arn:aws:ses:us-east-1:123456789012:identity/sender@example.com\",\r\n"
                + "    \"sendingAccountId\": \"123456789012\",\r\n"
                + "    \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "    \"destination\": [\r\n"
                + "      \"recipient@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"headersTruncated\": false,\r\n"
                + "    \"headers\": [\r\n"
                + "      {\r\n"
                + "        \"name\": \"From\",\r\n"
                + "        \"value\": \"sender@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"To\",\r\n"
                + "        \"value\": \"recipient@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Subject\",\r\n"
                + "        \"value\": \"Message sent from Amazon SES\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"MIME-Version\",\r\n"
                + "        \"value\": \"1.0\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Content-Type\",\r\n"
                + "        \"value\": \"multipart/mixed;  boundary=\\\"----=_Part_0_716996660.1476421336341\\\"\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"X-SES-MESSAGE-TAGS\",\r\n"
                + "        \"value\": \"myCustomTag1=myCustomTagValue1, myCustomTag2=myCustomTagValue2\"\r\n"
                + "      }\r\n"
                + "    ],\r\n"
                + "    \"commonHeaders\": {\r\n"
                + "      \"from\": [\r\n"
                + "        \"sender@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"to\": [\r\n"
                + "        \"recipient@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "      \"subject\": \"Message sent from Amazon SES\"\r\n"
                + "    },\r\n"
                + "    \"tags\": {\r\n"
                + "      \"ses:configuration-set\": [\r\n"
                + "        \"ConfigSet\"\r\n"
                + "      ],\r\n"
                + "      \"ses:source-ip\": [\r\n"
                + "        \"192.0.2.0\"\r\n"
                + "      ],\r\n"
                + "      \"ses:from-domain\": [\r\n"
                + "        \"example.com\"\r\n"
                + "      ],      \r\n"
                + "      \"ses:caller-identity\": [\r\n"
                + "        \"ses_user\"\r\n"
                + "      ],\r\n"
                + "      \"myCustomTag1\": [\r\n"
                + "        \"myCustomTagValue1\"\r\n"
                + "      ],\r\n"
                + "      \"myCustomTag2\": [\r\n"
                + "        \"myCustomTagValue2\"\r\n"
                + "      ]      \r\n"
                + "    }\r\n"
                + "  },\r\n"
                + "  \"send\": {}\r\n"
                + "}";
    }

    @DisplayName("Reject 테스트")
    @Test
    void reject() throws Exception {
        // given
        String json = createRejectSample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.REJECT);
        assertThat(actual.getMail()).isNotNull();
        assertThat(actual.getReject()).isNotNull();
        System.out.println(actual);
    }

    private String createRejectSample() {
        return "{\r\n"
                + "  \"eventType\": \"Reject\",\r\n"
                + "  \"mail\": {\r\n"
                + "    \"timestamp\": \"2016-10-14T17:38:15.211Z\",\r\n"
                + "    \"source\": \"sender@example.com\",\r\n"
                + "    \"sourceArn\": \"arn:aws:ses:us-east-1:123456789012:identity/sender@example.com\",\r\n"
                + "    \"sendingAccountId\": \"123456789012\",\r\n"
                + "    \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "    \"destination\": [\r\n"
                + "      \"sender@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"headersTruncated\": false,\r\n"
                + "    \"headers\": [\r\n"
                + "      {\r\n"
                + "        \"name\": \"From\",\r\n"
                + "        \"value\": \"sender@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"To\",\r\n"
                + "        \"value\": \"recipient@example.com\"\r\n"
                + "      },      \r\n"
                + "      {\r\n"
                + "        \"name\": \"Subject\",\r\n"
                + "        \"value\": \"Message sent from Amazon SES\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"MIME-Version\",\r\n"
                + "        \"value\": \"1.0\"\r\n"
                + "      },      \r\n"
                + "      {\r\n"
                + "        \"name\": \"Content-Type\",\r\n"
                + "        \"value\": \"multipart/mixed; boundary=\\\"qMm9M+Fa2AknHoGS\\\"\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"X-SES-MESSAGE-TAGS\",\r\n"
                + "        \"value\": \"myCustomTag1=myCustomTagValue1, myCustomTag2=myCustomTagValue2\"\r\n"
                + "      }  \r\n"
                + "    ],\r\n"
                + "    \"commonHeaders\": {\r\n"
                + "      \"from\": [\r\n"
                + "        \"sender@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"to\": [\r\n"
                + "        \"recipient@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "      \"subject\": \"Message sent from Amazon SES\"\r\n"
                + "    },\r\n"
                + "    \"tags\": {\r\n"
                + "      \"ses:configuration-set\": [\r\n"
                + "        \"ConfigSet\"\r\n"
                + "      ],\r\n"
                + "      \"ses:source-ip\": [\r\n"
                + "        \"192.0.2.0\"\r\n"
                + "      ],\r\n"
                + "      \"ses:from-domain\": [\r\n"
                + "        \"example.com\"\r\n"
                + "      ],    \r\n"
                + "      \"ses:caller-identity\": [\r\n"
                + "        \"ses_user\"\r\n"
                + "      ],\r\n"
                + "      \"myCustomTag1\": [\r\n"
                + "        \"myCustomTagValue1\"\r\n"
                + "      ],\r\n"
                + "      \"myCustomTag2\": [\r\n"
                + "        \"myCustomTagValue2\"\r\n"
                + "      ]      \r\n"
                + "    }\r\n"
                + "  },\r\n"
                + "  \"reject\": {\r\n"
                + "    \"reason\": \"Bad content\"\r\n"
                + "  }\r\n"
                + "}";
    }

    @DisplayName("Open 테스트")
    @Test
    void open() throws Exception {
        // given
        String json = createOpenSample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.OPEN);
        assertThat(actual.getMail()).isNotNull();
        assertThat(actual.getOpen()).isNotNull();
        System.out.println(actual);
    }

    private String createOpenSample() {
        return "{\r\n"
                + "  \"eventType\": \"Open\",\r\n"
                + "  \"mail\": {\r\n"
                + "    \"commonHeaders\": {\r\n"
                + "      \"from\": [\r\n"
                + "        \"sender@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "      \"subject\": \"Message sent from Amazon SES\",\r\n"
                + "      \"to\": [\r\n"
                + "        \"recipient@example.com\"\r\n"
                + "      ]\r\n"
                + "    },\r\n"
                + "    \"destination\": [\r\n"
                + "      \"recipient@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"headers\": [\r\n"
                + "      {\r\n"
                + "        \"name\": \"X-SES-CONFIGURATION-SET\",\r\n"
                + "        \"value\": \"ConfigSet\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"X-SES-MESSAGE-TAGS\",\r\n"
                + "        \"value\":\"myCustomTag1=myCustomValue1, myCustomTag2=myCustomValue2\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"From\",\r\n"
                + "        \"value\": \"sender@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"To\",\r\n"
                + "        \"value\": \"recipient@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Subject\",\r\n"
                + "        \"value\": \"Message sent from Amazon SES\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"MIME-Version\",\r\n"
                + "        \"value\": \"1.0\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Content-Type\",\r\n"
                + "        \"value\": \"multipart/alternative; boundary=\\\"XBoundary\\\"\"\r\n"
                + "      }\r\n"
                + "    ],\r\n"
                + "    \"headersTruncated\": false,\r\n"
                + "    \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "    \"sendingAccountId\": \"123456789012\",\r\n"
                + "    \"source\": \"sender@example.com\",\r\n"
                + "    \"tags\": {\r\n"
                + "      \"myCustomTag1\":[\r\n"
                + "        \"myCustomValue1\"\r\n"
                + "      ],\r\n"
                + "      \"myCustomTag2\":[\r\n"
                + "        \"myCustomValue2\"\r\n"
                + "      ],\r\n"
                + "      \"ses:caller-identity\": [\r\n"
                + "        \"IAM_user_or_role_name\"\r\n"
                + "      ],\r\n"
                + "      \"ses:configuration-set\": [\r\n"
                + "        \"ConfigSet\"\r\n"
                + "      ],\r\n"
                + "      \"ses:from-domain\": [\r\n"
                + "        \"example.com\"\r\n"
                + "      ],\r\n"
                + "      \"ses:source-ip\": [\r\n"
                + "        \"192.0.2.0\"\r\n"
                + "      ]\r\n"
                + "    },\r\n"
                + "    \"timestamp\": \"2017-08-09T21:59:49.927Z\"\r\n"
                + "  },\r\n"
                + "  \"open\": {\r\n"
                + "    \"ipAddress\": \"192.0.2.1\",\r\n"
                + "    \"timestamp\": \"2017-08-09T22:00:19.652Z\",\r\n"
                + "    \"userAgent\": \"Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_3 like Mac OS X) AppleWebKit/603.3.8 (KHTML, like Gecko) Mobile/14G60\"\r\n"
                + "  }\r\n"
                + "}";
    }

    @DisplayName("Click 테스트")
    @Test
    void click() throws Exception {
        // given
        String json = createClickSample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.CLICK);
        assertThat(actual.getMail()).isNotNull();
        assertThat(actual.getClick()).isNotNull();
        System.out.println(actual);
    }

    private String createClickSample() {
        return "{\r\n"
                + "  \"eventType\": \"Click\",\r\n"
                + "  \"click\": {\r\n"
                + "    \"ipAddress\": \"192.0.2.1\",\r\n"
                + "    \"link\": \"http://docs.aws.amazon.com/ses/latest/DeveloperGuide/send-email-smtp.html\",\r\n"
                + "    \"linkTags\": {\r\n"
                + "      \"samplekey0\": [\r\n"
                + "        \"samplevalue0\"\r\n"
                + "      ],\r\n"
                + "      \"samplekey1\": [\r\n"
                + "        \"samplevalue1\"\r\n"
                + "      ]\r\n"
                + "    },\r\n"
                + "    \"timestamp\": \"2017-08-09T23:51:25.570Z\",\r\n"
                + "    \"userAgent\": \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36\"\r\n"
                + "  },\r\n"
                + "  \"mail\": {\r\n"
                + "    \"commonHeaders\": {\r\n"
                + "      \"from\": [\r\n"
                + "        \"sender@example.com\"\r\n"
                + "      ],\r\n"
                + "      \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "      \"subject\": \"Message sent from Amazon SES\",\r\n"
                + "      \"to\": [\r\n"
                + "        \"recipient@example.com\"\r\n"
                + "      ]\r\n"
                + "    },\r\n"
                + "    \"destination\": [\r\n"
                + "      \"recipient@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"headers\": [\r\n"
                + "      {\r\n"
                + "        \"name\": \"X-SES-CONFIGURATION-SET\",\r\n"
                + "        \"value\": \"ConfigSet\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\":\"X-SES-MESSAGE-TAGS\",\r\n"
                + "        \"value\":\"myCustomTag1=myCustomValue1, myCustomTag2=myCustomValue2\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"From\",\r\n"
                + "        \"value\": \"sender@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"To\",\r\n"
                + "        \"value\": \"recipient@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Subject\",\r\n"
                + "        \"value\": \"Message sent from Amazon SES\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"MIME-Version\",\r\n"
                + "        \"value\": \"1.0\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Content-Type\",\r\n"
                + "        \"value\": \"multipart/alternative; boundary=\\\"XBoundary\\\"\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Message-ID\",\r\n"
                + "        \"value\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\"\r\n"
                + "      }\r\n"
                + "    ],\r\n"
                + "    \"headersTruncated\": false,\r\n"
                + "    \"messageId\": \"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "    \"sendingAccountId\": \"123456789012\",\r\n"
                + "    \"source\": \"sender@example.com\",\r\n"
                + "    \"tags\": {\r\n"
                + "      \"myCustomTag1\":[\r\n"
                + "        \"myCustomValue1\"\r\n"
                + "      ],\r\n"
                + "      \"myCustomTag2\":[\r\n"
                + "        \"myCustomValue2\"\r\n"
                + "      ],\r\n"
                + "      \"ses:caller-identity\": [\r\n"
                + "        \"ses_user\"\r\n"
                + "      ],\r\n"
                + "      \"ses:configuration-set\": [\r\n"
                + "        \"ConfigSet\"\r\n"
                + "      ],\r\n"
                + "      \"ses:from-domain\": [\r\n"
                + "        \"example.com\"\r\n"
                + "      ],\r\n"
                + "      \"ses:source-ip\": [\r\n"
                + "        \"192.0.2.0\"\r\n"
                + "      ]\r\n"
                + "    },\r\n"
                + "    \"timestamp\": \"2017-08-09T23:50:05.795Z\"\r\n"
                + "  }\r\n"
                + "}";
    }

    @DisplayName("Rendering Failure 테스트")
    @Test
    void renderingFailure() throws Exception {
        // given
        String json = createRenderingFailureSample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.RENDERING_FAILURE);
        assertThat(actual.getMail()).isNotNull();
        assertThat(actual.getFailure()).isNotNull();
        System.out.println(actual);
    }

    private String createRenderingFailureSample() {
        return "{\r\n"
                + "  \"eventType\":\"Rendering Failure\",\r\n"
                + "  \"mail\":{\r\n"
                + "    \"timestamp\":\"2018-01-22T18:43:06.197Z\",\r\n"
                + "    \"source\":\"sender@example.com\",\r\n"
                + "    \"sourceArn\":\"arn:aws:ses:us-east-1:123456789012:identity/sender@example.com\",\r\n"
                + "    \"sendingAccountId\":\"123456789012\",\r\n"
                + "    \"messageId\":\"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "    \"destination\":[\r\n"
                + "      \"recipient@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"headersTruncated\":false,\r\n"
                + "    \"tags\":{\r\n"
                + "      \"ses:configuration-set\":[\r\n"
                + "        \"ConfigSet\"\r\n"
                + "      ]\r\n"
                + "    }\r\n"
                + "  },\r\n"
                + "  \"failure\":{\r\n"
                + "    \"errorMessage\":\"Attribute 'attributeName' is not present in the rendering data.\",\r\n"
                + "    \"templateName\":\"MyTemplate\"\r\n"
                + "  }\r\n"
                + "}";
    }

    @DisplayName("Delivery Delay 테스트")
    @Test
    void deliveryDelay() throws Exception {
        // given
        String json = createDeliveryDelaySample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.DELIVERY_DELAY);
        assertThat(actual.getMail()).isNotNull();
        assertThat(actual.getDeliveryDelay()).isNotNull();
        System.out.println(actual);
    }

    private String createDeliveryDelaySample() {
        return "{\r\n"
                + "  \"eventType\": \"DeliveryDelay\",\r\n"
                + "  \"mail\":{\r\n"
                + "    \"timestamp\":\"2020-06-16T00:15:40.641Z\",\r\n"
                + "    \"source\":\"sender@example.com\",\r\n"
                + "    \"sourceArn\":\"arn:aws:ses:us-east-1:123456789012:identity/sender@example.com\",\r\n"
                + "    \"sendingAccountId\":\"123456789012\",\r\n"
                + "    \"messageId\":\"EXAMPLE7c191be45-e9aedb9a-02f9-4d12-a87d-dd0099a07f8a-000000\",\r\n"
                + "    \"destination\":[\r\n"
                + "      \"recipient@example.com\"\r\n"
                + "    ],\r\n"
                + "    \"headersTruncated\":false,\r\n"
                + "    \"tags\":{\r\n"
                + "      \"ses:configuration-set\":[\r\n"
                + "        \"ConfigSet\"\r\n"
                + "      ]\r\n"
                + "    }\r\n"
                + "  },\r\n"
                + "  \"deliveryDelay\": {\r\n"
                + "    \"timestamp\": \"2020-06-16T00:25:40.095Z\",\r\n"
                + "    \"delayType\": \"TransientCommunicationFailure\",\r\n"
                + "    \"expirationTime\": \"2020-06-16T00:25:40.914Z\",\r\n"
                + "    \"delayedRecipients\": [{\r\n"
                + "      \"emailAddress\": \"recipient@example.com\",\r\n"
                + "      \"status\": \"4.4.1\",\r\n"
                + "      \"diagnosticCode\": \"smtp; 421 4.4.1 Unable to connect to remote host\"\r\n"
                + "    }]\r\n"
                + "  }\r\n"
                + "}";
    }

    @DisplayName("Subscription 테스트")
    @Test
    void subscription() throws Exception {
        // given
        String json = createSubscriptionSample();

        // when
        final SesMessage actual = mapper.readValue(json, SesMessage.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNotificationType()).isEqualTo(NotificationType.SUBSCRIPTION);
        assertThat(actual.getMail()).isNotNull();
        assertThat(actual.getSubscription()).isNotNull();
        System.out.println(actual);
    }

    private String createSubscriptionSample() {
        return "{\r\n"
                + "  \"eventType\": \"Subscription\",\r\n"
                + "  \"mail\": {\r\n"
                + "    \"timestamp\": \"2022-01-12T01:00:14.340Z\",\r\n"
                + "    \"source\": \"sender@example.com\",\r\n"
                + "    \"sourceArn\": \"arn:aws:ses:us-east-1:123456789012:identity/sender@example.com\",\r\n"
                + "    \"sendingAccountId\": \"123456789012\",\r\n"
                + "    \"messageId\": \"EXAMPLEe4bccb684-777bc8de-afa7-4970-92b0-f515137b1497-000000\",\r\n"
                + "    \"destination\": [\"recipient@example.com\"],\r\n"
                + "    \"headersTruncated\": false,\r\n"
                + "    \"headers\": [\r\n"
                + "      {\r\n"
                + "        \"name\": \"From\",\r\n"
                + "        \"value\": \"sender@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"To\",\r\n"
                + "        \"value\": \"recipient@example.com\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Subject\",\r\n"
                + "        \"value\": \"Message sent from Amazon SES\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"MIME-Version\",\r\n"
                + "        \"value\": \"1.0\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Content-Type\",\r\n"
                + "        \"value\": \"text/html; charset=UTF-8\"\r\n"
                + "      },\r\n"
                + "      {\r\n"
                + "        \"name\": \"Content-Transfer-Encoding\",\r\n"
                + "        \"value\": \"7bit\"\r\n"
                + "      }\r\n"
                + "    ],\r\n"
                + "    \"commonHeaders\": {\r\n"
                + "      \"from\": [\"sender@example.com\"],\r\n"
                + "      \"to\": [\"recipient@example.com\"],\r\n"
                + "      \"messageId\": \"EXAMPLEe4bccb684-777bc8de-afa7-4970-92b0-f515137b1497-000000\",\r\n"
                + "      \"subject\": \"Message sent from Amazon SES\"\r\n"
                + "    },\r\n"
                + "    \"tags\": {\r\n"
                + "      \"ses:operation\": [\"SendEmail\"],\r\n"
                + "      \"ses:configuration-set\": [\"ConfigSet\"],\r\n"
                + "      \"ses:source-ip\": [\"192.0.2.0\"],\r\n"
                + "      \"ses:from-domain\": [\"example.com\"],\r\n"
                + "      \"ses:caller-identity\": [\"ses_user\"],\r\n"
                + "      \"myCustomTag1\": [\"myCustomValue1\"],\r\n"
                + "      \"myCustomTag2\": [\"myCustomValue2\"]\r\n"
                + "    }\r\n"
                + "  },\r\n"
                + "  \"subscription\": {\r\n"
                + "    \"contactList\": \"ContactListName\",\r\n"
                + "    \"timestamp\": \"2022-01-12T01:00:17.910Z\",\r\n"
                + "    \"source\": \"UnsubscribeHeader\",\r\n"
                + "    \"newTopicPreferences\": {\r\n"
                + "      \"unsubscribeAll\": true,\r\n"
                + "      \"topicSubscriptionStatus\": [\r\n"
                + "        {\r\n"
                + "          \"topicName\": \"ExampleTopicName\",\r\n"
                + "          \"subscriptionStatus\": \"OptOut\"\r\n"
                + "        }\r\n"
                + "      ]\r\n"
                + "    },\r\n"
                + "    \"oldTopicPreferences\": {\r\n"
                + "      \"unsubscribeAll\": false,\r\n"
                + "      \"topicSubscriptionStatus\": [\r\n"
                + "        {\r\n"
                + "          \"topicName\": \"ExampleTopicName\",\r\n"
                + "          \"subscriptionStatus\": \"OptOut\"\r\n"
                + "        }\r\n"
                + "      ]\r\n"
                + "    }\r\n"
                + "  }\r\n"
                + "}";
    }

}
