import ErrorMessage from '../../components/@common/ErrorMessage/styled';

import * as S from './styled';

export type TextAreaFieldProps = {
  id: string;
  label: string;
  answer: string;
  isSubmitted: boolean;
  handleChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  checkValidation: (text: string) => boolean;
};

const TextAreaField = ({
  id,
  label,
  answer,
  isSubmitted,
  handleChange,
  checkValidation,
}: TextAreaFieldProps) => {
  return (
    <S.Box>
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.TextArea id={id} onChange={handleChange} isError={isSubmitted && !checkValidation(answer)}>
        {answer}
      </S.TextArea>
      {isSubmitted && !checkValidation(answer) && (
        <ErrorMessage>10글자 이상을 입력해주세요.</ErrorMessage>
      )}
    </S.Box>
  );
};

export default TextAreaField;
