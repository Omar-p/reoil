package edu.tanta.fci.reoil.admin;

import edu.tanta.fci.reoil.admin.requests.AssignOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/worker")
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
@Slf4j
public class AdminWorkerController {

  private final AdminWorkerService adminWorkerService;

  @GetMapping("/disabled")
  public Iterable<WorkerDTO> getDisabledWorkersAccounts() {
    log.info("Get disabled workers accounts");
    return adminWorkerService.getDisabledWorkersAccounts();
  }

  @PostMapping("/disabled/{workerId}")
  public void enableWorkerAccount(@PathVariable Long workerId) {
    log.info("Enable worker account with id {}", workerId);
    adminWorkerService.enableWorkerAccount(workerId);
  }


  @GetMapping("/enabled")
  public Iterable<WorkerDTO> getEnabledWorkersAccounts() {
    log.info("Get enabled workers accounts");
    return adminWorkerService.getEnabledWorkersAccounts();
  }

  @PostMapping("/order")
  public void assignOrder(@RequestBody @Valid AssignOrderRequest assignOrderRequest ) {
    log.info("Assign order {} to worker {}", assignOrderRequest.orderId(), assignOrderRequest.workerId());
    adminWorkerService.assignOrder(assignOrderRequest);

  }
}
