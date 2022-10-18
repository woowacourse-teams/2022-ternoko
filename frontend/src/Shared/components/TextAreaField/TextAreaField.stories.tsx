import TextAreaField, { TextAreaFieldProps } from '.';
import { Story } from '@storybook/react';

export default {
  title: 'components/TextAreaField',
  component: TextAreaField,
};

const Template: Story<TextAreaFieldProps> = (args) => <TextAreaField {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  id: '1',
  label: '이번 면담을 통해 논의하고 싶은 내용',
};
