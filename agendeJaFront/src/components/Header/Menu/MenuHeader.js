import { RiArrowDownSFill, RiArrowUpSFill } from "react-icons/ri";
import { Menu, MenuButton, MenuList, MenuItem } from "@chakra-ui/react";
import "./MenuHeader.scss";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { logout } from "../../../redux/userSlice";
import { removeUser } from "../../../redux/userSliceDados";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { ChakraProvider } from "@chakra-ui/react";

export default function MenuHeader() {
  const dispatch = useDispatch();
  const userData = useSelector((state) => state?.userDados?.roles?.[0]?.name);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    if (userData === "ADMIN") {
      setIsAdmin(true);
    }
  }, [userData]);

  return (
    <ChakraProvider>
      <Menu>
        {({ isOpen }) => (
          <>
            <MenuButton isActive={isOpen} as="Button">
              {isOpen ? <RiArrowUpSFill /> : <RiArrowDownSFill />}
            </MenuButton>
            <MenuList>
              <MenuItem>
                <Link to="/minhaconta" className="anchor">
                  Minha conta
                </Link>
              </MenuItem>
              <MenuItem
                onClick={() => {
                  dispatch(logout());
                  dispatch(removeUser());
                }}
              >
                Sair
              </MenuItem>
              {isAdmin ? (
                <MenuItem>
                  <Link to="/administrador" className="anchor">
                    Administrador
                  </Link>
                </MenuItem>
              ) : (
                ""
              )}
            </MenuList>
          </>
        )}
      </Menu>
    </ChakraProvider>
  );
}
