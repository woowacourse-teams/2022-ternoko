import * as S from './styled';

import ErrorMessage from '@/components/@common/ErrorMessage/styled';

type InputAreaFieldProps = {
  id: string;
  label: string;
  value: string;
  message: string;
  handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  checkValidation: (text: string) => boolean;
};

const InputAreaField = ({
  id,
  label,
  value,
  message,
  handleChange,
  checkValidation,
}: InputAreaFieldProps) => {
  return (
    <div>
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.Input id={id} value={value} isError={!checkValidation(value)} onChange={handleChange} />
      {!checkValidation(value) && <ErrorMessage>{message}</ErrorMessage>}
    </div>
  );
};

export default InputAreaField;
