//package com.woowacourse.ternoko.support.alarm;
//
//public enum SlackMessageType {
//
//    CREW_CREATE("#00a310", "터놓고 - 면담 예약 생성", "%s이(가) 면담 예약을 생성하였습니다.", "%s와(과)의 면담을 생성하였습니다."),
//    CREW_UPDATE("#f2c744", "터놓고 - 면담 예약 수정", "%s이(가) 면담 예약을 수정하였습니다.", "%s와(과)의 면담을 수정하였습니다."),
//    COACH_CANCEL("#EB4C60", "터놓고 - 면담 예약 취소", "%s와(과)의 면담 예약을 취소하였습니다.", "%s이(가) 면담을 취소하였습니다. 재예약 해주세요."),
//    CREW_DELETE("#EB4C60", "터놓고 - 면담 예약 삭제", "%s이(가) 면담 예약을 삭제하였습니다.", "%s와(과)의 면담을 삭제하였습니다.");
//
//    private final String color;
//    private final String attachmentMessage;
//    private final String coachPreviewMessage;
//    private final String crewPreviewMessage;
//
//    SlackMessageType(String color, String attachmentMessage, String coachPreviewMessage, String crewPreviewMessage) {
//        this.color = color;
//        this.attachmentMessage = attachmentMessage;
//        this.coachPreviewMessage = coachPreviewMessage;
//        this.crewPreviewMessage = crewPreviewMessage;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public String getAttachmentMessage() {
//        return attachmentMessage;
//    }
//
//    public String getCoachPreviewMessage() {
//        return coachPreviewMessage;
//    }
//
//    public String getCrewPreviewMessage() {
//        return crewPreviewMessage;
//    }
//}
