export interface Coach {
  id: number;
  nickname: string;
  imageUrl: string;
}

export interface Reservation {
  id: number;
  crewNickname: string;
  coachNickname: string;
  interviewDate: string;
  interviewStartTime: string;
  interviewEndTime: string;
  location: string;
}
