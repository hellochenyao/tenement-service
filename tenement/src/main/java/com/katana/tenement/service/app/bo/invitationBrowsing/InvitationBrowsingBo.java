package com.katana.tenement.service.app.bo.invitationBrowsing;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class InvitationBrowsingBo {

    private int userId;

    private int invitationId;

    private LocalDateTime browsingTime;

}
