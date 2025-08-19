package com.atus.gerdp.configuration;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import com.atus.gerdp.core.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public CreateCategoryUseCase createCategoryUseCase(CategoryRepository r) {
        return new CreateCategoryUseCase(r);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase(CategoryRepository r) {
        return new ListCategoriesUseCase(r);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase(CategoryRepository r) {
        return new DeleteCategoryUseCase(r);
    }

    @Bean
    public RegisterExpenseUseCase registerExpenseUseCase(ExpenseRepository er, CategoryRepository cr) {
        return new RegisterExpenseUseCase(er, cr);
    }

    @Bean
    public ListExpensesUseCase listExpensesUseCase(ExpenseRepository r) {
        return new ListExpensesUseCase(r);
    }

    @Bean
    public DeleteExpenseUseCase deleteExpenseUseCase(ExpenseRepository r) {
        return new DeleteExpenseUseCase(r);
    }

    @Bean
    public CreateOrUpdateBudgetUseCase createOrUpdateBudgetUseCase(BudgetRepository br, CategoryRepository cr) {
        return new CreateOrUpdateBudgetUseCase(br, cr);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase(CategoryRepository categoryRepository) {
        return new UpdateCategoryUseCase(categoryRepository);
    }

    @Bean
    public UpdateExpenseUseCase updateExpenseUseCase(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        return new UpdateExpenseUseCase(expenseRepository, categoryRepository);
    }

    @Bean
    public ListBudgetsUseCase listBudgetsUseCase(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        return new ListBudgetsUseCase(budgetRepository, categoryRepository);
    }

    @Bean
    public DeleteBudgetUseCase deleteBudgetUseCase(BudgetRepository budgetRepository) {
        return new DeleteBudgetUseCase(budgetRepository);
    }

    @Bean
    public ListAvailableBudgetPeriodsUseCase listAvailableBudgetPeriodsUseCase(BudgetRepository budgetRepository) {
        return new ListAvailableBudgetPeriodsUseCase(budgetRepository);
    }

    @Bean // <-- ADICIONE ESTE BEAN
    public GetDashboardSummaryUseCase getDashboardSummaryUseCase(
            ExpenseRepository expenseRepository,
            BudgetRepository budgetRepository,
            CategoryRepository categoryRepository
    ) {
        return new GetDashboardSummaryUseCase(expenseRepository, budgetRepository, categoryRepository);
    }
}