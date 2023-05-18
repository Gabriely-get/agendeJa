import axios from "axios";

export default function useRegisterCompanyFilial() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function registerCompanyFilial(
    fantasyName,
    isCep,
    isLogradouro,
    isComplemento,
    isBairro,
    isCidade,
    isEstado,
    isNumero,
    userId,
    category,
    subCategories
  ) {
    let url = `${apiUrl}:5000/agenda/company/`;

    const response = await axios.post(url, {
      fantasyName: fantasyName,
      userId: userId,
      category: category,
      subCategories: subCategories,
      address: {
        cep: isCep,
        logradouro: isLogradouro,
        complemento: isComplemento,
        bairro: isBairro,
        localidade: isCidade,
        uf: isEstado,
        numero: isNumero,
      },
    });
    return response.data;
  }

  return {
    registerCompanyFilial,
  };
}
