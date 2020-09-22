const dict = {
  "C++": "CPP",
  JAVA: "JAVA",
  C: "C"
};

export const languageTranslator = language => {
  return dict[language];
};

export const createNoticeUrl = (noticeType, keyword, language, jobPosition) => {
  const query = new URLSearchParams(
    createNoticeObj(noticeType, keyword, language, jobPosition)
  );
  return `/notices?${query}`;
};

export const createNoticeObj = (noticeType, keyword, language, jobPosition) => {
  return {
    noticeType: noticeType || "JOB",
    keyword: keyword || "",
    language: language || "",
    jobPosition: jobPosition || ""
  };
};

export const urlValidator = url => {
  try {
    new URL(url);
    return true;
  } catch (error) {
    return false;
  }
};
