import { MemberRole } from '@/types/domain';

const LocalStorage = {
  getAccessToken: () => localStorage.getItem('accessToken'),
  getMemberRole: () => localStorage.getItem('memberRole') as MemberRole,
  setAccessToken: (accessToken: string) => localStorage.setItem('accessToken', accessToken),
  setMemberRole: (memberRole: MemberRole) => localStorage.setItem('memberRole', memberRole),
  removeAccessToken: () => localStorage.removeItem('accessToken'),
};

export default LocalStorage;
