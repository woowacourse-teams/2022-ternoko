import CoachLoginRegisterPage from '@/Coach/pages/CoachLoginRegisterPage';

import CrewLoginRegisterPage from '@/Crew/pages/CrewLoginRegisterPage';

import LocalStorage from '@/Shared/localStorage';

const LoginRegisterPage = () => {
  return LocalStorage.getMemberRole() === 'CREW' ? (
    <CrewLoginRegisterPage />
  ) : (
    <CoachLoginRegisterPage />
  );
};

export default LoginRegisterPage;
