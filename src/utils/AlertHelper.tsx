import { Alert } from 'react-native';

const AlertHelper = {
  showAlertOnlyCancel: (title, message, positiveButton) => {
    Alert.alert(
      title,
      message,
      [
        {
          text: positiveButton.text,
        }
      ],
      { cancelable: false }
    );
  },
};

export default AlertHelper;
