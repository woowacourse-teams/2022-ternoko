import Interview from '.';

import { InterviewType } from '@/types/domain';

import { Story } from '@storybook/react';

export default {
  title: 'components/Interview',
  component: Interview,
};

const Template = (args: InterviewType) => <Interview {...args} />;

export const Basic: Story<InterviewType> = Template.bind({});

Basic.args = {
  coachNickname: '브리',
  interviewStartTime: '14:00',
  interviewEndTime: '14:30',
  imageUrl:
    'https://user-images.githubusercontent.com/43205258/177765431-63d39896-c8e1-42e8-a229-08309849f2ff.png',
};
