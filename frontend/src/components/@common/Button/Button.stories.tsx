import { Story } from '@storybook/react';

import Button, { ButtonProps } from './styled';

export default {
  title: 'components/@common/Button',
  component: Button,
};

const Template = (args: ButtonProps) => <Button {...args}>안녕하세요</Button>;

export const Basic: Story<ButtonProps> = Template.bind({});

Basic.args = {
  width: '100px',
};
