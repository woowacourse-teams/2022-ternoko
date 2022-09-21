import * as S from './styled';

import ErrorMessage from '@/components/@common/ErrorMessage/styled';

export type TextAreaFieldProps = {
  id: string;
  label: string;
  value: string;
  maxLength: number;
  message: string;
  disabled?: boolean;
  handleChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  checkValidation: (text: string) => boolean;
};

const TextAreaField = ({
  id,
  label,
  value,
  maxLength,
  message,
  disabled,
  handleChange,
  checkValidation,
}: TextAreaFieldProps) => {
  return (
    <S.Box>
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.TextArea
        id={id}
        value={value}
        maxLength={maxLength}
        isError={!checkValidation(value)}
        disabled={disabled ?? false}
        onChange={handleChange}
      />
      <S.DescriptionBox>
        <p>{!checkValidation(value) && <ErrorMessage>{message}</ErrorMessage>}</p>
        <p>
          {value.length}/{maxLength}
        </p>
      </S.DescriptionBox>
    </S.Box>
  );
};

export default TextAreaField;
