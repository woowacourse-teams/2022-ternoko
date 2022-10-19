import LocalStorage from '../localStorage';

import { createContext, useContext, useState } from 'react';

import { getCoachInfoAPI } from '@/Coach/api';

import { getCrewInfoAPI } from '@/Crew/api';

import { CrewType as UserType } from '@/Types/domain';

type UserProviderProps = {
  children: React.ReactNode;
};

type UserStateType = {
  id: number;
  nickname: string;
  imageUrl: string;
};

type UserActionsType = {
  initializeUser: (callback: (() => void) | null) => void;
};

const UserStateContext = createContext<UserStateType | null>(null);
const UserActionsContext = createContext<UserActionsType | null>(null);

const UserProvider = ({ children }: UserProviderProps) => {
  const [id, setId] = useState(-1);
  const [nickname, setNickname] = useState('');
  const [imageUrl, setImageUrl] = useState('');

  const state = { id, nickname, imageUrl };

  const actions = {
    initializeUser: async (callback: (() => void) | null) => {
      const accessToken = LocalStorage.getAccessToken();
      const memberRole = LocalStorage.getMemberRole();

      if (!accessToken || !memberRole) return;

      const response = await (memberRole === 'CREW' ? getCrewInfoAPI() : getCoachInfoAPI());
      const user: UserType = response.data;

      setId(user.id);
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
