import Loading from '@/components/@common/Loading';

const TestPage = () => {
  return (
    <>
      <Loading
        additionalBoxStyle="position: fixed; left: 50%; top: 50%; transform: translate(-50%, -50%); width: 50%; height: 50%"
        profileSizeRem={25}
        animationDuration={1.2}
      />
    </>
  );
};

export default TestPage;
