import Loading from '@/components/@common/Loading';

type TernokoLoadingProps = {
  show: boolean;
};

const TernokoLoading = ({ show }: TernokoLoadingProps) => {
  if (!show) return null;

  return (
    <Loading
      additionalBoxStyle="position: fixed; left: 50%; top: 50%; transform: translate(-50%, -50%); width: 50%; height: 50%; background-color: unset;"
      profileSizeRem={25}
      animationDuration={1.2}
    />
  );
};

export default TernokoLoading;
