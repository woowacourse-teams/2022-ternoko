export interface CoachType {
  id: number;
  nickname: string;
  imageUrl: string;
}

export interface ReservationType {
  id: number;
  crewNickname: string;
  coachNickname: string;
  interviewDate: string;
  interviewStartTime: string;
  interviewEndTime: string;
  location: string;
}
