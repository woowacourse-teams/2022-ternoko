export interface CoachType {
  id: number;
  nickname: string;
  imageUrl: string;
}

export interface ReservationType {
  id: number;
  imageUrl: string;
  crewNickname: string;
  coachNickname: string;
  interviewDate: string;
  interviewStartTime: string;
  interviewEndTime: string;
  location: string;
}

interface InterviewQuestionType {
  question: string;
  answer: string;
}

export interface ReservationRequestBodyType {
  interviewDatetime: string;
  crewNickname: string;
  location: string;
  interviewQuestions: InterviewQuestionType[];
}
