const dict = {
  "C++": "CPP",
  JAVA: "JAVA",
  C: "C"
};
export const languageTranslator = language => {
  return dict[language];
};

export const dateParser = date => {
  console.log(date);
  if (date === "" || date === null) {
    return "";
  }
  return new Date(date)
    .toISOString()
    .slice(0, 16)
    .replace("T", " ");
};
