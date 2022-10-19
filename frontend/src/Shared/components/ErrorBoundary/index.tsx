import { Component } from 'react';
import { Navigate } from 'react-router-dom';

import { PATH } from '@/Shared/constants/path';

type ErrorBoundaryProps = {
  children?: React.ReactNode;
};

type ErrorBoundaryState = {
  hasError: boolean;
};

class ErrorBoundary extends Component<ErrorBoundaryProps, ErrorBoundaryState> {
  constructor(props: ErrorBoundaryProps) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError() {
    return { hasError: true };
  }

  render() {
    const { children } = this.props;

    if (this.state.hasError) {
      this.setState({
        hasError: false,
      });

      return <Navigate to={PATH.LOGIN} />;
    }

    return children;
  }
}

export default ErrorBoundary;
