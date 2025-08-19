package com.atus.gerdp.configuration;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import com.atus.gerdp.core.application.usecases.budget.CreateOrUpdateBudgetUseCase;
import com.atus.gerdp.core.application.usecases.budget.DeleteBudgetUseCase;
import com.atus.gerdp.core.application.usecases.budget.ListAvailableBudgetPeriodsUseCase;
import com.atus.gerdp.core.application.usecases.budget.ListBudgetsUseCase;
import com.atus.gerdp.core.application.usecases.category.CreateCategoryUseCase;
import com.atus.gerdp.core.application.usecases.category.DeleteCategoryUseCase;
import com.atus.gerdp.core.application.usecases.category.ListCategoriesUseCase;
import com.atus.gerdp.core.application.usecases.category.UpdateCategoryUseCase;
import com.atus.gerdp.core.application.usecases.dashboard.GetDashboardSummaryUseCase;
import com.atus.gerdp.core.application.usecases.expense.DeleteExpenseUseCase;
import com.atus.gerdp.core.application.usecases.expense.ListExpensesUseCase;
import com.atus.gerdp.core.application.usecases.expense.RegisterExpenseUseCase;
import com.atus.gerdp.core.application.usecases.expense.UpdateExpenseUseCase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração que "conecta" as camadas da arquitetura.
 * Aqui, dizemos ao Spring como criar os Casos de Uso (a lógica)
 * e quais implementações de repositório (o acesso ao banco) eles devem usar.
 */
@Configuration
public class BeanConfiguration {

    // --- CATEGORY BEANS ---

    @Bean
    public CreateCategoryUseCase createCategoryUseCase(CategoryRepository categoryRepository) {
        return new CreateCategoryUseCase(categoryRepository);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase(CategoryRepository categoryRepository) {
        return new ListCategoriesUseCase(categoryRepository);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase(CategoryRepository categoryRepository) {
        return new DeleteCategoryUseCase(categoryRepository);
    }
    
    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase(CategoryRepository categoryRepository) {
        return new UpdateCategoryUseCase(categoryRepository);
    }

    // --- EXPENSE BEANS ---

    @Bean
    public RegisterExpenseUseCase registerExpenseUseCase(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        return new RegisterExpenseUseCase(expenseRepository, categoryRepository);
    }

    @Bean
    public ListExpensesUseCase listExpensesUseCase(ExpenseRepository expenseRepository) {
        return new ListExpensesUseCase(expenseRepository);
    }

    @Bean
    public DeleteExpenseUseCase deleteExpenseUseCase(ExpenseRepository expenseRepository) {
        return new DeleteExpenseUseCase(expenseRepository);
    }

    @Bean
    public UpdateExpenseUseCase updateExpenseUseCase(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        return new UpdateExpenseUseCase(expenseRepository, categoryRepository);
    }

    // --- BUDGET BEANS ---

    @Bean
    public CreateOrUpdateBudgetUseCase createOrUpdateBudgetUseCase(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        return new CreateOrUpdateBudgetUseCase(budgetRepository, categoryRepository);
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

    // --- DASHBOARD BEAN ---

    @Bean
    public GetDashboardSummaryUseCase getDashboardSummaryUseCase(
            ExpenseRepository expenseRepository,
            BudgetRepository budgetRepository,
            CategoryRepository categoryRepository
    ) {
        return new GetDashboardSummaryUseCase(expenseRepository, budgetRepository, categoryRepository);
    }
}
