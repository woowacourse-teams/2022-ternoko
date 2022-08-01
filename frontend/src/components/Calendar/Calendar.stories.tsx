import Calendar, { CalendarProps } from '.';

import { Story } from '@storybook/react';

export default {
  title: 'components/@common/Calendar',
  component: Calendar,
};

const Template = (args: CalendarProps) => <Calendar {...args} />;

export const Basic: Story<CalendarProps> = Template.bind({});

Basic.args = {
  rerenderKey: 1,
};
