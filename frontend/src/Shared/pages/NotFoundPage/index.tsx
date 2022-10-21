import { Navigate } from 'react-router-dom';

import * as S from './styled';

import Button from '@/Shared/components/Button/styled';

import { PATH } from '@/Shared/constants/path';
import LocalStorage from '@/Shared/localStorage';

type NotFoundPageType = 'DENY' | 'DEFAULT';

type NotFoundPageProps = {
  type: NotFoundPageType;
};

const NotFoundPage = ({ type }: NotFoundPageProps) => {
  const role = LocalStorage.getMemberRole();

  if (!role) {
    return <Navigate to={PATH.LOGIN} />;
  }

  const handleClickButton = () => {
    if (type === 'DEFAULT') {
      location.href = PATH.LOGIN;
    } else if (type === 'DENY') {
      location.href = role === 'CREW' ? PATH.CREW_HOME : PATH.COACH_HOME;
    }
  };

  return (
    <S.Box>
      <div>
        <p>
          {type === 'DEFAULT'
            ? '해당 페이지는 찾을 수 없습니다ㅠㅠㅠㅠ😭'
            : '접근 권한이 없다구용ㅠㅠㅠㅠ😭'}
        </p>
        <Button onClick={handleClickButton}>
          {type === 'DEFAULT' ? '로그인 페이지로 가즈앗🏃‍♂️' : '홈으로 가즈앗🏃‍♂️'}
        </Button>
      </div>
    </S.Box>
  );
};

export default NotFoundPage;
