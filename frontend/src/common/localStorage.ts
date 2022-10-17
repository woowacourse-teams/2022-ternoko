import { MemberRoleType } from './types/domain';

const LocalStorage = {
  getAccessToken: () => localStorage.getItem('accessToken'),
  getMemberRole: () => localStorage.getItem('memberRole') as MemberRoleType,
  setAccessToken: (accessToken: string) => localStorage.setItem('accessToken', accessToken),
  setMemberRole: (memberRole: MemberRoleType) => localStorage.setItem('memberRole', memberRole),
  removeAccessToken: () => localStorage.removeItem('accessToken'),
};

export default LocalStorage;
