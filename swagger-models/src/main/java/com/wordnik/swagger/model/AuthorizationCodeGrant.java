package com.wordnik.swagger.model;

public class AuthorizationCodeGrant extends GrantType {

  private final TokenRequestEndpoint tokenRequestEndpoint;
  private final TokenEndpoint tokenEndpoint;

  public AuthorizationCodeGrant(TokenRequestEndpoint tokenRequestEndpoint, TokenEndpoint tokenEndpoint) {
    super("authorization_code");
    this.tokenRequestEndpoint = tokenRequestEndpoint;
    this.tokenEndpoint = tokenEndpoint;
  }

  public TokenRequestEndpoint tokenRequestEndpoint() {
    return tokenRequestEndpoint;
  }

  public TokenEndpoint tokenEndpoint() {
    return tokenEndpoint;
  }
}
