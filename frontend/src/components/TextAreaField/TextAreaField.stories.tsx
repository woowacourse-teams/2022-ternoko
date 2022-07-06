import { Story } from '@storybook/react';

import TextAreaField, { TextAreaFieldProps } from '.';

export default {
  title: 'components/TextAreaField',
  component: TextAreaField,
};

const Template: Story<TextAreaFieldProps> = (args) => <TextAreaField {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  id: 'test',
  label: '이번 면담을 통해 논의하고 싶은 내용',
};
