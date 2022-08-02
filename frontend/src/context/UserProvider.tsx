import { createContext, useContext, useState } from 'react';

import { CrewType as UserType } from '@/types/domain';

import { getCoachInfoAPI, getCrewInfoAPI } from '@/api';
import LocalStorage from '@/localStorage';

type UserProviderProps = {
  children: React.ReactNode;
};

type UserStateType = {
  nickname: string;
  imageUrl: string;
};

type UserActionsType = {
  initializeUser: (callback: (() => void) | null) => void;
};

const UserStateContext = createContext<UserStateType | null>(null);
const UserActionsContext = createContext<UserActionsType | null>(null);

const UserProvider = ({ children }: UserProviderProps) => {
  const [nickname, setNickname] = useState('');
  const [imageUrl, setImageUrl] = useState('');

  const state = { nickname, imageUrl };

  const actions = {
    initializeUser: async (callback: (() => void) | null) => {
      const accessToken = LocalStorage.getAccessToken();
      const memberRole = LocalStorage.getMemberRole();

      if (!accessToken || !memberRole) return;

      const response = await (memberRole === 'CREW' ? getCrewInfoAPI() : getCoachInfoAPI());
      const user: UserType = response.data;

      setNickname(user.nickname);
      setImageUrl(user.imageUrl);

      if (typeof callback === 'function') callback();
    },
  };

  return (
    <UserStateContext.Provider value={state}>
      <UserActionsContext.Provider value={actions}>{children}</UserActionsContext.Provider>
    </UserStateContext.Provider>
  );
};

export const useUserState = () => useContext(UserStateContext) as UserStateType;

export const useUserActions = () => useContext(UserActionsContext) as UserActionsType;

export default UserProvider;
