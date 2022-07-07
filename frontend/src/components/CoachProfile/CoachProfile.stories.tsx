import { Story } from '@storybook/react';
import CoachProfile, { CoachProfileProps } from '.';

export default {
  title: 'components/CoachProfile',
  component: CoachProfile,
};

const Template = (args: CoachProfileProps) => <CoachProfile {...args} />;

export const Basic: Story<CoachProfileProps> = Template.bind({});

Basic.args = {
  id: 1,
  nickname: '록바',
  imageUrl: 'https://blog.kakaocdn.net/dn/FSvHG/btrzdoAbEI0/WA1kfeo9BFC8n8GOe39U31/img.webp',
  currentCoachId: 1,
  handleClickProfile: () => {},
};
