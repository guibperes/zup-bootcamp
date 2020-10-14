package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import java.util.Optional;

import com.zup.bootcamp.guibperes.bank.base.BaseRepository;

public interface AccountProposalRepository extends BaseRepository<AccountProposal> {

  Optional<AccountProposal> findByEmail(String email);

  Optional<AccountProposal> findByCpf(String cpf);
}
