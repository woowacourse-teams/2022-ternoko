import React, { useEffect } from 'react';
import ReactDOM from 'react-dom';

import * as S from './styled';

import { useToastActions, useToastState } from '@/context/ToastProvider';

const Toast = () => {
  const { toasts } = useToastState();
  const { hideToast } = useToastActions();

  useEffect(() => {
    const interval = setInterval(() => {
      if (toasts.length) {
        hideToast(toasts[0].id);
      }
    }, 3000);

    return () => {
      clearInterval(interval);
    };
  }, [toasts]);

  return ReactDOM.createPortal(
    <S.Box length={toasts.length} isReduction={toasts.some(({ status }) => status === 'DEAD')}>
      {toasts.map((toast) => (
        <React.Fragment key={toast.id}>
          <S.ToastFrame toastStatus={toast.status} />

          <S.ToastBox toastStatus={toast.status}>
            <S.Toast title={toast.title} toastStatus={toast.status}>
              <S.Content>
                {toast.title === 'SUCCESS' ? (
                  <picture>
                    <source srcSet="/assets/icon/success.avif" type="image/avif" />
                    <S.TitleIcon src="/assets/icon/success.png" alt="성공 아이콘" />
                  </picture>
                ) : (
                  <picture>
                    <source srcSet="/assets/icon/error.avif" type="image/avif" />
                    <S.TitleIcon src="/assets/icon/error.png" alt="에러 아이콘" />
                  </picture>
                )}
                <div>
                  <S.Title>{toast.title === 'SUCCESS' ? '성공 😁' : '실패 😂'}</S.Title>
                  <p>{toast.description}</p>
                </div>
              </S.Content>

              <S.DeleteButton onClick={() => hideToast(toast.id)}>✖️</S.DeleteButton>
            </S.Toast>
          </S.ToastBox>
        </React.Fragment>
      ))}
    </S.Box>,
    document.getElementById('toast') as HTMLElement,
  );
};

export default Toast;
