import { createSlice } from "@reduxjs/toolkit";

export const slice = createSlice({
  name: "userDados",
  initialState: {
    id: null,
    email: null,
    password: null,
    firstName: null,
    lastName: null,
    birthday: null,
    cpf: null,
    phone: null,
    isActive: null,
    createAt: null,
    updateAt: null,
    roles: null,
  },
  reducers: {
    addInfoUser(state, { payload }) {
      return {
        ...state,
        id: payload.data.id,
        email: payload.data.email,
        password: payload.data.password,
        firstName: payload.data.firstName,
        lastName: payload.data.lastName,
        birthday: payload.data.birthday,
        cpf: payload.data.cpf,
        phone: payload.data.phone,
        isActive: payload.data.isActive,
        createAt: payload.data.createAt,
        updateAt: payload.data.updateAt,
        roles: payload.data.roles,
      };
    },
    removeUser(state) {
      return {
        ...state,
        id: null,
        email: null,
        password: null,
        firstName: null,
        lastName: null,
        birthday: null,
        cpf: null,
        phone: null,
        isActive: null,
        createAt: null,
        updateAt: null,
        roles: null,
      };
    },
  },
});

export const { addInfoUser, removeUser } = slice.actions;

export const selectUser = (state) => state.userDados;

export default slice.reducer;
