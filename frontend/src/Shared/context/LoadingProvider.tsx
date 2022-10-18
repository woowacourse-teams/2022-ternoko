import { createContext, useContext, useState } from 'react';

type LoadingStateType = {
  show: boolean;
};

type LoadingActionsType = {
  onLoading: () => void;
  offLoading: () => void;
};

type LoadingProviderProps = {
  children: React.ReactNode;
};

const LoadingStateContext = createContext<LoadingStateType | null>(null);
const LoadingActionsContext = createContext<LoadingActionsType | null>(null);

const LoadingProvider = ({ children }: LoadingProviderProps) => {
  const [show, setShow] = useState(false);

  const state = { show };

  const actions = {
    onLoading: () => setShow(true),
    offLoading: () => setShow(false),
  };

  return (
    <LoadingStateContext.Provider value={state}>
      <LoadingActionsContext.Provider value={actions}>{children}</LoadingActionsContext.Provider>
    </LoadingStateContext.Provider>
  );
};

export const useLoadingState = () => useContext(LoadingStateContext) as LoadingStateType;

export const useLoadingActions = () => useContext(LoadingActionsContext) as LoadingActionsType;

export default LoadingProvider;
