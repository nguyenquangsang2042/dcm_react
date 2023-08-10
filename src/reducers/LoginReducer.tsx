import { SET_LOADING, SET_LOGIN } from "../types/ActiontTypes";

const initialState = {
  login: false,
  loading: false,
};
export  const loginReducer = (state = initialState.login, action) => {
  switch (action.type) {
    case SET_LOGIN:
      return action.payload;
    default:
      return state;
  }
};

export const loadingReducer = (state = initialState.loading, action) => {
  switch (action.type) {
    case SET_LOADING:
      return action.payload;
    default:
      return state;
  }
};
