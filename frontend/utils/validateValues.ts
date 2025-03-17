const validateValues = <T>(value: T, allowedValues: T[], defaultValue: T): T => {
  return allowedValues.includes(value) ? value : defaultValue;
};

export default validateValues;
