import * as S from './styled';

const logoURL =
  'https://user-images.githubusercontent.com/19251499/180158202-4d757b47-d804-45e4-b73b-9d6e13fe844b.png';
const slackURL = 'https://a.slack-edge.com/80588/marketing/img/icons/icon_slack_hash_colored.png';

const LoginPage = () => {
  const handleClickLoginButton = () => {
    location.href =
      'https://slack.com/openid/connect/authorize?scope=openid%20email%20profile&amp;response_type=code&amp;redirect_uri=https://15.164.218.98:8080/api/login&amp;client_id=3756998338916.3821665111344 HTTP/1.1';
  };

  return (
    <S.Box>
      <S.LeftBox>
        <div>
          <h3>면담은 찐하게, 예약은 손쉽게</h3>
          <h2>면담 예약 서비스</h2>
          <S.LogoBox>
            <h1>터놓고</h1>
            <img src={logoURL} alt="프로필 로고" />
          </S.LogoBox>
          <S.LoginButton onClick={handleClickLoginButton}>
            <img src={slackURL} alt="슬랙 로고" />
            슬랙 로그인
          </S.LoginButton>
        </div>
      </S.LeftBox>
      <S.RightBox></S.RightBox>
    </S.Box>
  );
};
export default LoginPage;
