package io.github.libedi.awssdkutils.ses.event.domain;

import java.util.List;

import io.github.libedi.awssdkutils.ses.event.type.BounceSubType;
import io.github.libedi.awssdkutils.ses.event.type.ComplaintFeedbackType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * SesComplaint
 *
 * @author "Sangjun,Park"
 *
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class SesComplaint implements TimeLocalizable {

    private List<Recipient>       complainedRecipients;
    private String                timestamp;
    private String                feedbackId;
    /**
     * @return null or {@link BounceSubType#ON_ACCOUNT_SUPPRESSION_LIST}
     */
    private BounceSubType         complaintSubType;
    private String                userAgent;
    private ComplaintFeedbackType complaintFeedbackType;
    private String                arrivalDate;

}
