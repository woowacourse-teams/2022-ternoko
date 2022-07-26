import * as S from './styled';

import ErrorMessage from '../../components/@common/ErrorMessage/styled';

type InputAreaFieldProps = {
  id: string;
  label: string;
  value: string;
  isSubmitted: boolean;
  handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  checkValidation: (text: string) => boolean;
};

const InputAreaField = ({
  id,
  label,
  value,
  isSubmitted,
  handleChange,
  checkValidation,
}: InputAreaFieldProps) => {
  return (
    <div>
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.Input
        id={id}
        value={value}
        isError={isSubmitted && !checkValidation(value)}
        onChange={handleChange}
      />
      {isSubmitted && !checkValidation(value) && (
        <ErrorMessage>1글자 이상을 입력해주세요.</ErrorMessage>
      )}
    </div>
  );
};

export default InputAreaField;