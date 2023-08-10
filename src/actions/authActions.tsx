
export const login = (user: { username: string; password: string; }) => {
  return {
    type: user.password==''|| user.username==''?'LOGOUT':'LOGIN_SUCCESS',
    payload: user.password==''|| user.username==''?null:user,
  };
};

export const logout = () => {
  return {
    type: 'LOGOUT',
  };
};
