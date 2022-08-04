import { createContext, useContext, useState } from 'react';

export type TitleType = 'SUCCESS' | 'ERROR';

export type ToastStatus = 'ALIVE' | 'DEAD';

type Toast = {
  id: number;
  title: TitleType;
  description: string;
  status: ToastStatus;
};

type ToastStateType = {
  toasts: Toast[];
};

type ToastActionsType = {
  showToast: (title: TitleType, description: string) => void;
  hideToast: (id: number) => void;
};

type ToastProviderProps = {
  children: React.ReactNode;
};

const ToastStateContext = createContext<ToastStateType | null>(null);
const ToastActionsContext = createContext<ToastActionsType | null>(null);

const ToastProvider = ({ children }: ToastProviderProps) => {
  const [toasts, setToasts] = useState<Toast[]>([]);

  const state = { toasts };

  const actions = {
    showToast(title: TitleType, description: string) {
      setToasts((prev) => [
        ...prev,
        {
          id: Date.now(),
          title,
          description,
          status: 'ALIVE',
        },
      ]);
    },
    hideToast(id: number) {
      const nextToasts: Toast[] = toasts.map((toast) =>
        toast.id === id ? { ...toast, status: 'DEAD' } : toast,
      );

      setToasts(nextToasts);

      setTimeout(() => {
        setToasts(nextToasts.filter((toast) => toast.status === 'ALIVE'));
      }, 400);
    },
  };

  return (
    <ToastStateContext.Provider value={state}>
      <ToastActionsContext.Provider value={actions}>{children}</ToastActionsContext.Provider>
    </ToastStateContext.Provider>
  );
};

export const useToastState = () => useContext(ToastStateContext) as ToastStateType;

export const useToastActions = () => useContext(ToastActionsContext) as ToastActionsType;

export default ToastProvider;
