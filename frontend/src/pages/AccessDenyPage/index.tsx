import { Navigate } from 'react-router-dom';

import * as S from './styled';

import Button from '@/components/@common/Button/styled';

import { PAGE } from '@/constants';
import LocalStorage from '@/localStorage';

const AccessDenyPage = () => {
  const role = LocalStorage.getMemberRole();

  if (!role) {
    return <Navigate to={PAGE.LOGIN} />;
  }

  const handleClickButton = () => {
    location.href = role === 'CREW' ? PAGE.CREW_HOME : PAGE.COACH_HOME;
  };

  return (
    <S.Box>
      <div>
        <p>접근 권한이 없다구용ㅠㅠㅠㅠ😭</p>
        <Button onClick={handleClickButton}>홈으로 가즈앗🏃‍♂️</Button>
      </div>
    </S.Box>
  );
};

export default AccessDenyPage;
