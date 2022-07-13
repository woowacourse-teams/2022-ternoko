import { Story } from '@storybook/react';

import Reservation from '.';

import { ReservationType } from 'types/domain';

export default {
  title: 'components/Reservation',
  component: Reservation,
};

const Template = (args: ReservationType) => <Reservation {...args} />;

export const Basic: Story<ReservationType> = Template.bind({});

Basic.args = {
  coachNickname: '브리',
  interviewDate: '2022-07-04',
  interviewStartTime: '14:00',
  interviewEndTime: '14:30',
  location: '잠실',
  imageUrl:
    'https://user-images.githubusercontent.com/43205258/177765431-63d39896-c8e1-42e8-a229-08309849f2ff.png',
};
