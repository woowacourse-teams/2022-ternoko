import CoachMyPage from '@/Coach/pages/CoachMyPage';

import CrewMyPage from '@/Crew/pages/CrewMyPage';

import LocalStorage from '@/Shared/localStorage';

const MyPage = () => {
  return LocalStorage.getMemberRole() === 'CREW' ? <CrewMyPage /> : <CoachMyPage />;
};

export default MyPage;
