import * as S from './styled';

export type TextAreaFieldProps = {
  id: string;
  label: string;
};

const TextAreaField = ({ id, label }: TextAreaFieldProps) => {
  return (
    <S.Box>
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.TextArea id={id}></S.TextArea>
    </S.Box>
  );
};

export default TextAreaField;
