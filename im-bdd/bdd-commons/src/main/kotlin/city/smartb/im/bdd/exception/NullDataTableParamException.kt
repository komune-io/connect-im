package city.smartb.im.bdd.exception

class NullDataTableParamException(
    param: String
): IllegalDataTableParamException(param, "Should not be null")
