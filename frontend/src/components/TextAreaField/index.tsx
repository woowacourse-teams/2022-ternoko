import React from 'react';

import * as S from './styled';

import ErrorMessage from '@/components/@common/ErrorMessage/styled';

export type TextAreaFieldProps = {
  id: string;
  label: string;
  value: string;
  maxLength: number;
  message: string;
  isSubmitted: boolean;
  handleChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  checkValidation: (text: string) => boolean;
};

const TextAreaField = ({
  id,
  label,
  value,
  maxLength,
  message,
  isSubmitted,
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
        onChange={handleChange}
        isError={isSubmitted && !checkValidation(value)}
      />
      <S.DescriptionBox>
        <p>{isSubmitted && !checkValidation(value) && <ErrorMessage>{message}</ErrorMessage>}</p>
        <p>
          {value.length}/{maxLength}
        </p>
      </S.DescriptionBox>
    </S.Box>
  );
};

export default TextAreaField;
